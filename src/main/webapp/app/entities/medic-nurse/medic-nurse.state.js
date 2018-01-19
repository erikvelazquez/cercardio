(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('medic-nurse', {
            parent: 'entity',
            url: '/medic-nurse',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.medicNurse.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medic-nurse/medic-nurses.html',
                    controller: 'MedicNurseController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('medicNurse');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('medic-nurse-detail', {
            parent: 'medic-nurse',
            url: '/medic-nurse/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.medicNurse.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medic-nurse/medic-nurse-detail.html',
                    controller: 'MedicNurseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('medicNurse');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MedicNurse', function($stateParams, MedicNurse) {
                    return MedicNurse.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'medic-nurse',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('medic-nurse-detail.edit', {
            parent: 'medic-nurse-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medic-nurse/medic-nurse-dialog.html',
                    controller: 'MedicNurseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MedicNurse', function(MedicNurse) {
                            return MedicNurse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medic-nurse.new', {
            parent: 'medic-nurse',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medic-nurse/medic-nurse-dialog.html',
                    controller: 'MedicNurseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idnurse: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('medic-nurse', null, { reload: 'medic-nurse' });
                }, function() {
                    $state.go('medic-nurse');
                });
            }]
        })
        .state('medic-nurse.edit', {
            parent: 'medic-nurse',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medic-nurse/medic-nurse-dialog.html',
                    controller: 'MedicNurseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MedicNurse', function(MedicNurse) {
                            return MedicNurse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medic-nurse', null, { reload: 'medic-nurse' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medic-nurse.delete', {
            parent: 'medic-nurse',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medic-nurse/medic-nurse-delete-dialog.html',
                    controller: 'MedicNurseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MedicNurse', function(MedicNurse) {
                            return MedicNurse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medic-nurse', null, { reload: 'medic-nurse' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
