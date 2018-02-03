(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('medical-procedures', {
            parent: 'entity',
            url: '/medical-procedures',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.medical_Procedures.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medical-procedures/medical-procedures.html',
                    controller: 'Medical_ProceduresController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('medical_Procedures');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('medical-procedures-detail', {
            parent: 'medical-procedures',
            url: '/medical-procedures/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.medical_Procedures.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medical-procedures/medical-procedures-detail.html',
                    controller: 'Medical_ProceduresDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('medical_Procedures');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Medical_Procedures', function($stateParams, Medical_Procedures) {
                    return Medical_Procedures.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'medical-procedures',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('medical-procedures-detail.edit', {
            parent: 'medical-procedures-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medical-procedures/medical-procedures-dialog.html',
                    controller: 'Medical_ProceduresDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medical_Procedures', function(Medical_Procedures) {
                            return Medical_Procedures.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medical-procedures.new', {
            parent: 'medical-procedures',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medical-procedures/medical-procedures-dialog.html',
                    controller: 'Medical_ProceduresDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                catalogkey: null,
                                pronombre: null,
                                procveedia: null,
                                proedadia: null,
                                procveedfa: null,
                                proedadfa: null,
                                sextype: null,
                                pornivela: null,
                                procedimientotype: null,
                                proprincipal: null,
                                procapitulo: null,
                                proseccion: null,
                                procategoria: null,
                                prosubcateg: null,
                                progrupolc: null,
                                proescauses: null,
                                pronumcauses: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('medical-procedures', null, { reload: 'medical-procedures' });
                }, function() {
                    $state.go('medical-procedures');
                });
            }]
        })
        .state('medical-procedures.edit', {
            parent: 'medical-procedures',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medical-procedures/medical-procedures-dialog.html',
                    controller: 'Medical_ProceduresDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medical_Procedures', function(Medical_Procedures) {
                            return Medical_Procedures.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medical-procedures', null, { reload: 'medical-procedures' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medical-procedures.delete', {
            parent: 'medical-procedures',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medical-procedures/medical-procedures-delete-dialog.html',
                    controller: 'Medical_ProceduresDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Medical_Procedures', function(Medical_Procedures) {
                            return Medical_Procedures.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medical-procedures', null, { reload: 'medical-procedures' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
