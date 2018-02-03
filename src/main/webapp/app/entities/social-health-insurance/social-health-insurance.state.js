(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('social-health-insurance', {
            parent: 'entity',
            url: '/social-health-insurance',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.socialHealthInsurance.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/social-health-insurance/social-health-insurances.html',
                    controller: 'SocialHealthInsuranceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('socialHealthInsurance');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('social-health-insurance-detail', {
            parent: 'social-health-insurance',
            url: '/social-health-insurance/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.socialHealthInsurance.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/social-health-insurance/social-health-insurance-detail.html',
                    controller: 'SocialHealthInsuranceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('socialHealthInsurance');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SocialHealthInsurance', function($stateParams, SocialHealthInsurance) {
                    return SocialHealthInsurance.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'social-health-insurance',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('social-health-insurance-detail.edit', {
            parent: 'social-health-insurance-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-health-insurance/social-health-insurance-dialog.html',
                    controller: 'SocialHealthInsuranceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SocialHealthInsurance', function(SocialHealthInsurance) {
                            return SocialHealthInsurance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('social-health-insurance.new', {
            parent: 'social-health-insurance',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-health-insurance/social-health-insurance-dialog.html',
                    controller: 'SocialHealthInsuranceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                isactive: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('social-health-insurance', null, { reload: 'social-health-insurance' });
                }, function() {
                    $state.go('social-health-insurance');
                });
            }]
        })
        .state('social-health-insurance.edit', {
            parent: 'social-health-insurance',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-health-insurance/social-health-insurance-dialog.html',
                    controller: 'SocialHealthInsuranceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SocialHealthInsurance', function(SocialHealthInsurance) {
                            return SocialHealthInsurance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('social-health-insurance', null, { reload: 'social-health-insurance' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('social-health-insurance.delete', {
            parent: 'social-health-insurance',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-health-insurance/social-health-insurance-delete-dialog.html',
                    controller: 'SocialHealthInsuranceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SocialHealthInsurance', function(SocialHealthInsurance) {
                            return SocialHealthInsurance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('social-health-insurance', null, { reload: 'social-health-insurance' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
