(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('occupation', {
            parent: 'entity',
            url: '/occupation',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.occupation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/occupation/occupations.html',
                    controller: 'OccupationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('occupation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('occupation-detail', {
            parent: 'occupation',
            url: '/occupation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.occupation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/occupation/occupation-detail.html',
                    controller: 'OccupationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('occupation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Occupation', function($stateParams, Occupation) {
                    return Occupation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'occupation',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('occupation-detail.edit', {
            parent: 'occupation-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/occupation/occupation-dialog.html',
                    controller: 'OccupationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Occupation', function(Occupation) {
                            return Occupation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('occupation.new', {
            parent: 'occupation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/occupation/occupation-dialog.html',
                    controller: 'OccupationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                isactive: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('occupation', null, { reload: 'occupation' });
                }, function() {
                    $state.go('occupation');
                });
            }]
        })
        .state('occupation.edit', {
            parent: 'occupation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/occupation/occupation-dialog.html',
                    controller: 'OccupationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Occupation', function(Occupation) {
                            return Occupation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('occupation', null, { reload: 'occupation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('occupation.delete', {
            parent: 'occupation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/occupation/occupation-delete-dialog.html',
                    controller: 'OccupationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Occupation', function(Occupation) {
                            return Occupation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('occupation', null, { reload: 'occupation' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
