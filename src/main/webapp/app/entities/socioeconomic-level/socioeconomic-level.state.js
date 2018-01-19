(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('socioeconomic-level', {
            parent: 'entity',
            url: '/socioeconomic-level',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.socioeconomicLevel.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/socioeconomic-level/socioeconomic-levels.html',
                    controller: 'SocioeconomicLevelController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('socioeconomicLevel');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('socioeconomic-level-detail', {
            parent: 'socioeconomic-level',
            url: '/socioeconomic-level/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.socioeconomicLevel.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/socioeconomic-level/socioeconomic-level-detail.html',
                    controller: 'SocioeconomicLevelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('socioeconomicLevel');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SocioeconomicLevel', function($stateParams, SocioeconomicLevel) {
                    return SocioeconomicLevel.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'socioeconomic-level',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('socioeconomic-level-detail.edit', {
            parent: 'socioeconomic-level-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/socioeconomic-level/socioeconomic-level-dialog.html',
                    controller: 'SocioeconomicLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SocioeconomicLevel', function(SocioeconomicLevel) {
                            return SocioeconomicLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('socioeconomic-level.new', {
            parent: 'socioeconomic-level',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/socioeconomic-level/socioeconomic-level-dialog.html',
                    controller: 'SocioeconomicLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                name: null,
                                isactive: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('socioeconomic-level', null, { reload: 'socioeconomic-level' });
                }, function() {
                    $state.go('socioeconomic-level');
                });
            }]
        })
        .state('socioeconomic-level.edit', {
            parent: 'socioeconomic-level',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/socioeconomic-level/socioeconomic-level-dialog.html',
                    controller: 'SocioeconomicLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SocioeconomicLevel', function(SocioeconomicLevel) {
                            return SocioeconomicLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('socioeconomic-level', null, { reload: 'socioeconomic-level' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('socioeconomic-level.delete', {
            parent: 'socioeconomic-level',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/socioeconomic-level/socioeconomic-level-delete-dialog.html',
                    controller: 'SocioeconomicLevelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SocioeconomicLevel', function(SocioeconomicLevel) {
                            return SocioeconomicLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('socioeconomic-level', null, { reload: 'socioeconomic-level' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
