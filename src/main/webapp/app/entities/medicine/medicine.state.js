(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('medicine', {
            parent: 'entity',
            url: '/medicine',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.medicine.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medicine/medicines.html',
                    controller: 'MedicineController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('medicine');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('medicine-detail', {
            parent: 'medicine',
            url: '/medicine/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.medicine.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medicine/medicine-detail.html',
                    controller: 'MedicineDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('medicine');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Medicine', function($stateParams, Medicine) {
                    return Medicine.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'medicine',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('medicine-detail.edit', {
            parent: 'medicine-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medicine/medicine-dialog.html',
                    controller: 'MedicineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medicine', function(Medicine) {
                            return Medicine.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medicine.new', {
            parent: 'medicine',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medicine/medicine-dialog.html',
                    controller: 'MedicineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                tipoinsumo: null,
                                dentrofueracuadro: null,
                                nogrupoterapeutico: null,
                                nombregrupoterapeutico: null,
                                nivelatencion: null,
                                clavecodigo: null,
                                subclavecodigo: null,
                                nombregenerico: null,
                                formafarmaceutica: null,
                                concentracion: null,
                                presentacion: null,
                                principalindicacion: null,
                                demasindicaciones: null,
                                tipoactualizacion: null,
                                noactualizacion: null,
                                descripcion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('medicine', null, { reload: 'medicine' });
                }, function() {
                    $state.go('medicine');
                });
            }]
        })
        .state('medicine.edit', {
            parent: 'medicine',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medicine/medicine-dialog.html',
                    controller: 'MedicineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medicine', function(Medicine) {
                            return Medicine.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medicine', null, { reload: 'medicine' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medicine.delete', {
            parent: 'medicine',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medicine/medicine-delete-dialog.html',
                    controller: 'MedicineDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Medicine', function(Medicine) {
                            return Medicine.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medicine', null, { reload: 'medicine' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
